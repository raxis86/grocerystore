package Services.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by raxis on 27.12.2016.
 */
public class NoSavedInDbException extends Exception{
    private static final Logger logger = LoggerFactory.getLogger(NoSavedInDbException.class);
}
