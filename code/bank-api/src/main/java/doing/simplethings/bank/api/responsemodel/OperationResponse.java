package doing.simplethings.bank.api.responsemodel;

import java.util.List;
import java.util.Optional;

public class OperationResponse<V>{
    private final Optional<V> value;
    private final Optional<List<String>> errors;

    private OperationResponse(Optional<V> value, Optional<List<String>> errors) {
        this.value = value;
        this.errors = errors;
    }

    public static <V> OperationResponse<V> withValue(V value) {
        return new OperationResponse<>(Optional.of(value), Optional.empty());
    }

    public static <V> OperationResponse<V> withErrors(List<String> errors) {
        return new OperationResponse<>(Optional.empty(), Optional.of(errors));
    }

    public boolean hasValue() {
        return value.isPresent();
    }

    public boolean hasError() {
        return errors.isPresent();
    }

    public V getValue() {
        return value.get();
    }

    public List<String> getErrors() {
        return errors.get();
    }
}
