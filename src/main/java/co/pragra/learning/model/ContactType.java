package co.pragra.learning.model;

public enum ContactType {
    PERSONAL, BUSINESS, OTHER;

    public static ContactType findByName(String name) {
        ContactType result = null;
        for (ContactType ct : values()) {
            if (ct.name().equalsIgnoreCase(name)) {
                result = ct;
                break;
            }
        }
        return result;
    }
}
