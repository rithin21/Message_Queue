package broker;

//enums basically say these are the only accepted answers,it provides type safety
public enum MessageStatus {
    READY,
    IN_FLIGHT,
    ACKNOWLEDGED,
    FAILED,
}
