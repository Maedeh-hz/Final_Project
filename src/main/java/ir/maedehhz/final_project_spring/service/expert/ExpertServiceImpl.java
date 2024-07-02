package ir.maedehhz.final_project_spring.service.expert;

import ir.maedehhz.final_project_spring.exception.*;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import ir.maedehhz.final_project_spring.repository.ExpertRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService{

    private final ExpertRepository repository;

    @Override
    public Expert save(Expert expert, String imagePath) {
        if (repository.existsByUsername(expert.getEmail()))
            throw new DuplicateInfoException
                    (String.format("User with username %s exists!", expert.getEmail()));

        if (!validatePass(expert.getPassword()))
            throw new InvalidInputException("Users entrance password is invalid!");

        if (!validateEmail(expert.getEmail()))
            throw new InvalidInputException("Users entrance email is invalid!");

        if (!imagePath.endsWith(".png"))
            throw new ImageFormatException("The image format should be in PNG!");

        expert.setImage(getBytesForExpert(imagePath));

        if (expert.getImage().length > 300 * 1024)
            throw new ImageLengthOutOfBoundException("The uploaded image size is more than 300KB!");

        expert.setUsername(expert.getEmail());
        expert.setRegistrationDate(LocalDate.now());
        return repository.save(expert);
    }

    @Override
    public Expert updateScore(Expert expert) {
        return repository.save(expert);
    }

    @Override
    public Expert findByUsername(String username) {
        if (!repository.existsByUsername(username))
            throw new NotFoundException(String.format("User with username %s not found!", username));
        return repository.findByUsername(username);
    }

    @Override
    public Expert updatePassword(long expertId, String previousPass, String newPass, String newPass2) {
        Expert expert = findById(expertId);
        if (!previousPass.equals(expert.getPassword()))
            throw new PasswordMismatchException("Wrong password!");
        if (!newPass.equals(newPass2))
            throw new PasswordMismatchException("The first and second passwords are not the same!");
        if (!validatePass(newPass))
            throw new InvalidInputException("Invalid password!");
        if (validatePass(newPass))
            expert.setPassword(newPass);
        return repository.save(expert);
    }

    @Override
    public Expert updateStatusToVerified(long expertId) {
        Expert byId = findById(expertId);
        byId.setStatus(ExpertStatus.VERIFIED);
        return repository.save(byId);
    }

    @Override
    public Expert updateStatusToUnverified(long expertId) {
        Expert byId = findById(expertId);
        byId.setStatus(ExpertStatus.UNVERIFIED);
        return repository.save(byId);
    }

    @Override
    public Expert findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Expert with id %s couldn't be found.", id)));
    }

    private boolean validatePass(String pass){
        Pattern validPass = Pattern.compile("^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validPass.matcher(pass);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    public byte[] getBytesForExpert(String path) {
        byte[] array;
        try {
            array = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return array;
    }


}
