package api;

public class ActualityCurrency {
    public String name;
    public double average;
    public double buying;
    public double selling;

//    public ActualityCurrency(double average, double buying, double selling) {
//        this.average = average;
//        this.buying = buying;
//        this.selling = selling;
//    }

    @Override
    public String toString() {
        return "ActualityCurrency{" +
                "name='" + name + '\'' +
                ", average=" + average +
                ", buying=" + buying +
                ", selling=" + selling +
                '}';
    }
}
