package enums;

public enum loginPageTexts {
    USER_NOT_EXISTS("Either username or password is incorrect", type.ERROR), PASSWORD_SAFETY("Password should contains letters,digits and !@#$%^& and should be within 8-32 chars", type.ERROR), WELCOME("welcome to the game", type.SUCCESS), USER_EXISTS("a user exists with the same username", type.ERROR), USERNAME_CHANGED("Username changed successfully", type.SUCCESS), PASSWORD_CHANGED("Password changed successfully", type.SUCCESS);

    private final String text;
    private final type state;

    loginPageTexts(String text, type state) {
        this.text = text;
        this.state = state;
    }

    public String getText() {
        return text;
    }

    public type getState() {
        return state;
    }

    public enum type {
        SUCCESS, ERROR
    }


}
