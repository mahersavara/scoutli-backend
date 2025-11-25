package com.scoutli.service;

import com.scoutli.api.dto.CommentDTO;
import com.scoutli.domain.entity.Comment;
import com.scoutli.domain.entity.Discovery;
import com.scoutli.domain.entity.User;
import com.scoutli.domain.repository.CommentRepository;
import com.scoutli.domain.repository.DiscoveryRepository;
import com.scoutli.domain.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
@Slf4j
public class CommentService {

    @Inject
    CommentRepository commentRepository;
    @Inject
    DiscoveryRepository discoveryRepository;
    @Inject
    UserRepository userRepository;

    public List<CommentDTO> getCommentsByDiscoveryId(Long discoveryId) {
        return commentRepository.list("discovery.id", discoveryId).stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentDTO createComment(Long discoveryId, CommentDTO.CreateRequest request, String userEmail) {
        log.info("Creating comment for discovery {} by user {}", discoveryId, userEmail);
        User user = userRepository.findByEmail(userEmail);
        Discovery discovery = discoveryRepository.findById(discoveryId);

        if (user == null || discovery == null) {
            throw new IllegalArgumentException("User or Discovery not found");
        }

        Comment comment = new Comment();
        comment.setContent(request.content);
        comment.setUser(user);
        comment.setDiscovery(discovery);

        commentRepository.persist(comment);
        return toDTO(comment);
    }

    private CommentDTO toDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getEmail(),
                comment.getCreatedAt());
    }
}
