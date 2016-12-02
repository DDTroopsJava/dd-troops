package cz.fi.muni.pa165.ddtroops.rest.tools;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by pstanko.
 * @author pstanko
 */
@XmlRootElement
public class ApiError {

    private List<String> errors;

    public ApiError() {
    }

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
