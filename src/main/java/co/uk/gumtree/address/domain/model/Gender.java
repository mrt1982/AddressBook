package co.uk.gumtree.address.domain.model;

public enum Gender {
    MALE("Male"),
    FEMALE("Female");

    public String getGenderName() {
        return genderName;
    }

    private final String genderName;

    Gender(String genderName) {
        this.genderName = genderName;
    }

    public static Gender fromGenderName(String genderName) {
        for (Gender gender : values()) {
            if (gender.getGenderName().equals(genderName)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Gender with name " + genderName + " not found");
    }
}
