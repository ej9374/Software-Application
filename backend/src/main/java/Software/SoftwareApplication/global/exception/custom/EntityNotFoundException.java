package Software.SoftwareApplication.global.exception.custom;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(String entityName, Object value) {
        super(entityName + "이 '" + value + "'인 " + entityName + "이 존재하지 않습니다.");
    }

}
