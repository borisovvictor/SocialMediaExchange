package servicelayer;

/**
 * Created by Victor on 23.05.2016.
 */
public class Util {
    public enum Result
    {
        SUCCEED,
        FAILED,
        INVALID_PARAMS,
        USER_ALREADY_EXIST,
        USER_NOT_EXIST,
        INTERNAL_ERROR,
    }

    public static int currentLoggedUser;
}
