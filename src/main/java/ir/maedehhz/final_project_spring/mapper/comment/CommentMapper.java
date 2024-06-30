package ir.maedehhz.final_project_spring.mapper.comment;

import ir.maedehhz.final_project_spring.dto.comment.CommentSaveRequest;
import ir.maedehhz.final_project_spring.dto.comment.CommentSaveResponse;
import ir.maedehhz.final_project_spring.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    Comment commentSaveRequestToModel(CommentSaveRequest request);

    CommentSaveResponse modelToCommentSaveResponse(Comment comment);
}
