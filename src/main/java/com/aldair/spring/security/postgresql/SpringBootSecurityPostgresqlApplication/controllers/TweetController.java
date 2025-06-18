package com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.controllers;

import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.EReaction;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.Tweet;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.models.User;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.CommentResponse;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.TweetResponse;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.payload.response.UserMinResponse;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.TweetRepository;
import com.aldair.spring.security.postgresql.SpringBootSecurityPostgresqlApplication.repository.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private ImageUploadService imageUploadService;

    @GetMapping("/all")
    public ResponseEntity<List<TweetResponse>> getAllTweets() {
        List<Tweet> tweets = tweetRepository.findAllWithComments();
        List<TweetResponse> response = new ArrayList<>();

        for (Tweet tweet : tweets) {
            List<CommentResponse> commentDTOs = tweet.getComments().stream().map(comment ->
                new CommentResponse(
                    comment.getId(),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getUser().getUsername()
                )
            ).toList();

            Map<String, Long> rawCount = tweet.getReactions().stream()
                .filter(r -> r.getReaction() != null && r.getReaction().getName() != null)
                .collect(Collectors.groupingBy(
                    r -> r.getReaction().getName().name(),
                    Collectors.counting()
                ));

            Map<String, Integer> reactionCount = new LinkedHashMap<>();
            for (EReaction er : EReaction.values()) {
                long count = rawCount.getOrDefault(er.name(), 0L);
                reactionCount.put(er.name(), (int) count);
            }

            reactionCount = reactionCount.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue,
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));

            TweetResponse tweetDTO = new TweetResponse(
                tweet.getId(),
                tweet.getTweet(),
                //tweet.getImageUrl(),
                new UserMinResponse(
                tweet.getPostedBy().getId(),
                tweet.getPostedBy().getUsername()
                ),
                commentDTOs,        // Asegúrate de que `commentDTOs` esté correctamente inicializado
                reactionCount,      // Asegúrate de que `reactionCount` esté correctamente inicializado
                tweet.getCreatedAt() // Incluir la fecha de creación en la respuesta
);


            response.add(tweetDTO);
        }

        return ResponseEntity.ok(response);
    }

/*
@PostMapping("/create")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<?> createTweet(
        @RequestParam("tweet") String tweetText,
        @RequestParam(value = "image", required = false) MultipartFile imageFile,
        Authentication authentication) {

    try {
        Optional<User> userOpt = userRepository.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = userOpt.get();
        Tweet tweet = new Tweet(tweetText);
        tweet.setPostedBy(user);

        
        if (imageFile != null && !imageFile.isEmpty()) {
            String url = imageUploadService.uploadImage(imageFile);
            tweet.setImageUrl(url);
        }

        // Guardar el tweet en la base de datos (la fecha se asigna automáticamente por @CreationTimestamp)
        Tweet saved = tweetRepository.save(tweet);

        // Crear la respuesta que incluirá el tweet y la fecha de creación
        TweetResponse response = new TweetResponse(
            saved.getId(),
            saved.getTweet(),
            //saved.getImageUrl(),
            new UserMinResponse(user.getId(), user.getUsername()),
            List.of(),             // Lista vacía para comentarios
            Map.of(),              // Mapa vacío para reacciones
            saved.getCreatedAt()   // Incluir la fecha de creación en la respuesta
);

        return ResponseEntity.ok(response);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}*/


@PostMapping("/create")
@PreAuthorize("hasRole('USER')")
public ResponseEntity<?> createTweet(
        @RequestParam("tweet") String tweetText,  // Usamos @RequestParam para recibir el texto del tweet
        Authentication authentication) {

    try {
        // Obtener el usuario autenticado
        Optional<User> userOpt = userRepository.findByUsername(authentication.getName());
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        User user = userOpt.get();
        Tweet tweet = new Tweet(tweetText);  // Usar el texto del tweet recibido
        tweet.setPostedBy(user);

        // Guardar el tweet en la base de datos
        Tweet saved = tweetRepository.save(tweet);

        // Crear la respuesta que incluirá el tweet y la fecha de creación
        TweetResponse response = new TweetResponse(
            saved.getId(),
            saved.getTweet(),
            new UserMinResponse(user.getId(), user.getUsername()),
            List.of(),             // Lista vacía para comentarios
            Map.of(),              // Mapa vacío para reacciones
            saved.getCreatedAt()   // Incluir la fecha de creación en la respuesta
        );

        return ResponseEntity.ok(response);  // Devolver el tweet creado
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
    }
}




    /*
    @PostMapping("/upload")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        try {
            String url = imageUploadService.uploadImage(file);
            return ResponseEntity.ok(Map.of("imageUrl", url));
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al subir imagen: " + e.getMessage());
        }
    }*/

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteTweet(@PathVariable Long id, Authentication auth) {
        Optional<Tweet> tweetOpt = tweetRepository.findById(id);

        if (tweetOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Tweet no encontrado"));
        }

        Tweet tweet = tweetOpt.get();

        if (!tweet.getPostedBy().getUsername().equals(auth.getName())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "No tienes permiso para eliminar este tweet"));
        }

        // No se elimina imagen de Cloudinary (opcional: usar API para eso)

        tweetRepository.delete(tweet);
        return ResponseEntity.ok(Map.of("message", "Tweet eliminado correctamente 🗑️"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateTweet(
            @PathVariable Long id,
            @RequestParam("tweet") String newText,
           /* @RequestParam(value = "image", required = false) MultipartFile newImage, 
            @RequestParam(value = "removeImage", required = false, defaultValue = "false") boolean removeImage,*/
            Authentication authentication) {

        Optional<Tweet> tweetOpt = tweetRepository.findById(id);
        if (tweetOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Tweet no encontrado"));
        }

        Tweet tweet = tweetOpt.get();
        String currentUsername = authentication.getName();
        if (!tweet.getPostedBy().getUsername().equals(currentUsername)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", "No puedes editar este tweet"));
        }

        tweet.setTweet(newText);
        
        /*
        if (removeImage) {
            tweet.setImageUrl(null);
        }
        
        if (newImage != null && !newImage.isEmpty()) {
            try {
                String newUrl = imageUploadService.uploadImage(newImage);
                tweet.setImageUrl(newUrl);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", "Error al guardar imagen: " + e.getMessage())
                );
            }
        }
        */
        tweetRepository.save(tweet);
        return ResponseEntity.ok(Map.of("message", "Tweet actualizado correctamente ✨"));
    }
}
