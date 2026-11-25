package restaurant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Drink extends MenuItem {

    // Private static final constants
    private static final int MIN_VOLUME = 4;
    private static final int MAX_VOLUME = 64;
    private static final float MAX_STRENGTH = 0.15f;
    private static final String[] types = {"soda", "juice", "tea", "beer"};
    private static final double ALCOHOLIC_UPCHARGE = 2.50; //this is per oz
    private static final int ALCOHOL_CALORIES = 98;

    // Private instance fields
    private String drinkFlavor;
    private boolean isAlcoholic;
    private float alcoholicStrength;

    /**
     * Default Constructor
     * 
     */

    public Drink(
            String itemId,
            String name,
            String description,
            double basePrice,
            int calories,
            float strength,
            boolean isAlcoholic, 
            String drinkFlavor,
            ) {
        super(itemId, name, description, basePrice, calories);
        if (isAlcoholic)
            validateStrength(strength);
        this.alcoholicStrength = strength;
        this.isAlcoholic = isAlcoholic;
        this.drinkFlavor = drinkFlavor;
    }

    public Drink(
        String itemId,
        String name,
        String description,
        double basePrice,
        int calories,
        int sweetness,
        boolean nuts){
    this(itemId, name, description, basePrice, calories, sweetness, false, "None", nuts);
        }

    // ===================================================================================================================
    // Overridden Abstract Methods from MenuItem
    //====================================================================================================================
    
    @Override 
    public int calculatePrepTime(){
        int prep = 6;
        if(withIceCream){
            prep+=1;
        }
        prep+=toppings.size();
        return prep;
    }

    @Override
    public double calculateCoursePrice(){
        double total = getBasePrice();
        if(withIceCream){
            total+=ICE_CREAM_UPCHARGE;
        }
        total+= toppings.size() * TOPPING_UPCHARGE;
        return roundCurrency(total);
    }

    @Override 
    public String getServingInstructions(){
        String aLaModeText = withIceCream
            ? "a la mode with 1 scoop of " + iceCreamFlavor + " ice cream"
            : "";
        String toppingsText = toppings.isEmpty()
            ? ""
            : "topped with " + String.join(" ,", toppings);  
    String nutWarning = containsNuts ? " [CONTAINS NUTS]" : "";
    return String.format(
        "DESSERT [Sweetness: %d/5]%s: Serve %s, %s.", 
                sweetnessLevel,
                nutWarning,
                aLaModeText,
                toppingsText);
    }

    // ===================================================================================================================
    // Unique Methods for Drink
    //======================================================================================================

    public boolean validateStrength(float strength) {
        
        if(strength < 0.0f || strength > MAX_STRENGTH){
            throw new IllegalArgumentException(
                "An alcoholic beverage's strength must be between 0.0 and " + MAX_STRENGTH);
        }
    }
}
