//Alan Himes
//ahimes1@cnm.edu
//TipsterCalc.java
package himesp1.javareview;

/**
 *
 * @author ahimes1
 */
public class TipsterCalc {
    private double bill, tipPercent, tip, total, totalEach;
    private int people;
    
    public TipsterCalc() {
        bill = 0;
        people= 0;
        tipPercent = 0;
        tip = 0;
        total = 0;
        totalEach = 0;
    }
    
    public void setBill(double bill) {
        this.bill = bill;
    }
    
    public void setPeople(int people) {
        this.people = people;
    }
    
    public void setTipPercent(double tipPercent) {
        this.tipPercent = tipPercent;
    }
    
    public double getTip() {
        return tip;
    }
    
    public double getTotal() {
        return total;
    }
    
    public double getTotalEach() {
        return totalEach;
    }
    
    public void setInputData(double bill, int people, double tipPercent) {
        this.bill = bill;
        this.people = people;
        this.tipPercent = tipPercent;
        calc();
    }
    
    private void calc() {
        tip = bill * (tipPercent / 100);
        total = bill + tip;
        totalEach = total / people;
    }
}
