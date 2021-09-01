import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class RestaurantTest {
    Restaurant restaurant;

	@BeforeEach
	private void addRestaurantAndMenu() {
		LocalTime openingTime = LocalTime.parse("10:30:00");
		LocalTime closingTime = LocalTime.parse("22:00:00");
		restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
		restaurant.addToMenu("Sweet corn soup", 200);
		restaurant.addToMenu("Vegetable lasagne", 300);
		restaurant.addToMenu("Pizza", 400);
	}
    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
		Restaurant spiedRestaurant = Mockito.spy(restaurant);
		when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("20:30:00"));
		assertTrue(spiedRestaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
		Restaurant spiedRestaurant = Mockito.spy(restaurant);
		when(spiedRestaurant.getCurrentTime()).thenReturn(LocalTime.parse("23:30:00"));
		assertFalse(spiedRestaurant.isRestaurantOpen());
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	@Test
	public void passing_valid_item_names_will_return_actual_order_value() throws itemNotFoundException {
		List<String> itemNames = new ArrayList<String>();
		itemNames.add("Sweet corn soup");
		itemNames.add("Vegetable lasagne");
		itemNames.add("Pizza");
		int actualCost = restaurant.calculateOrderValue(itemNames);
		assertEquals(900, actualCost);
	}

	@Test
	public void passing_invalid_item_names_should_throw_exception() throws itemNotFoundException {
		List<String> itemNames = new ArrayList<String>();
		itemNames.add("Burger");
		itemNames.add("Vegetable lasagne");
		assertThrows(itemNotFoundException.class, () -> {
			restaurant.calculateOrderValue(itemNames);
		});
	}
}