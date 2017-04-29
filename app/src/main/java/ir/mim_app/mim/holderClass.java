package ir.mim_app.mim;

/**
 * Created by NFP_7037 on 4/29/2017.
 */

public class holderClass {

    String name;
    String index;


    public holderClass(String first,String second){

        this.name = first;
        this.index = second;

    }

    public String first(){
        return name;
    }

    public String second(){
        return index;
    }

}
