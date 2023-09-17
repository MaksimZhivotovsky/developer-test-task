package max.example.exceptions;

public class UserNotFoundException extends BignessException{
    public UserNotFoundException(String message) {
        super(message);
    }
}
