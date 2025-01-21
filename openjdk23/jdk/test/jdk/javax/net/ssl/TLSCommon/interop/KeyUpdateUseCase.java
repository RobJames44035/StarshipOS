/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * The SSL/TLS communication parameters on TLS 1.3 key update.
 */
public class KeyUpdateUseCase extends UseCase {

    private KeyUpdateRequest keyUpdateRequest = KeyUpdateRequest.NOT_REQUESTED;

    public static KeyUpdateUseCase newInstance() {
        return new KeyUpdateUseCase();
    }

    public KeyUpdateRequest getKeyUpdateRequest() {
        return keyUpdateRequest;
    }

    public KeyUpdateUseCase setKeyUpdateRequest(
            KeyUpdateRequest keyUpdateRequest) {
        this.keyUpdateRequest = keyUpdateRequest;
        return this;
    }

    @Override
    public String toString() {
        return Utilities.join(Utilities.PARAM_DELIMITER,
                super.toString(),
                Utilities.joinNameValue("KeyUpdateRequest", keyUpdateRequest.name));
    }
}
