package Assignment2;

/**
 * This is a Assessment class with type and weight info
 */
public class Assessment {
    private char type;
    private int weight;

    /**
     * This is a default constructor
     */
    private Assessment() {

    } // end of default constructor

    /**
     * This is a constructor with type and weight info
     *
     * @param type
     * @param weight
     */
    private Assessment(char type, int weight) {
        this();
        this.type = type;
        this.weight = weight;
    } // end of constructor

    public static Assessment getInstance(char type, int weight) {
        return new Assessment(type, weight);
    } // end of getInstance

    /**
     * This is an override equals method
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Assessment)) return false;
        Assessment that = (Assessment) obj;
        return type == that.type &&
                weight == that.weight;
    } // end of equals

} // end of Assessment