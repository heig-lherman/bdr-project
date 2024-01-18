package heig.bdr.choochoo.api.util;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResultValue<T> {

    private final T value;

    public static <T> ResultValue<T> of(T value) {
        return new ResultValue<>(value);
    }
}
