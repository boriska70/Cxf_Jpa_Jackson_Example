package com.bb.service;/**
 * Created with IntelliJ IDEA.
 * User: belozovs
 * Date: 7/2/14
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bb.dal.dao.PlaceDao;
import com.bb.dal.dao.PlaceDatabaseException;
import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/spring/test-context.xml"})
public class PlaceServiceTest {

    @Mock
    PlaceDao placeDao;

    @InjectMocks
    private PlaceService placeService = new PlaceServiceImpl();

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllPlacesTest() throws Exception {
        List<Place> places = placeService.getAllPlaces();
        assertEquals(0, places.size());
        verify(placeDao, times(1)).getAllPlaces();
    }

    @Test
    public void addPlaceTest() throws Exception {
        Place place = new Place();
        place.setName("MyName");
        place.setNotes("MyNotes");

        List<Address> addressList = new ArrayList<Address>();

        Place place1 = placeService.addPlace(place);
        assertEquals(place, place1);

        when(placeDao.getPlaceByName(place.getName())).thenReturn(place);
        Place place2 = placeService.getPlaceByName(place.getName());
        assertEquals(place, place2);

        verify(placeDao, times(1)).addPlace(any(Place.class));
        verify(placeDao, times(2)).getPlaceByName(anyString()); //1 - here and 1 inside addPlace
    }

    @Test
    public void findPlacesByPatternTest() throws Exception {
        Place place = new Place();
        place.setName("MyName");
        place.setNotes("MyNotes");

        List<Place> placesList = new ArrayList<Place>();
        placesList.add(place);

        String pattern = "am";
        when(placeDao.getPlacesByPattern(pattern)).thenReturn(placesList);
        List<Place> placesFound = placeService.findPlacesByPattern(pattern);
        assertNotNull(placesFound);
        verify(placeDao, times(1)).getPlacesByPattern(pattern);
        assertEquals(place, placesFound.get(0));
    }

    @Test
    public void updatePlaceTest() throws Exception {
        Place place = new Place();
        place.setName("MyName");
        place.setNotes("MyNotes");

        Address address = new Address();
        address.setAlias("main");
        address.setAddress("street");
        address.setMetro("station");
        address.setNotes("address note");

        Address address1 = new Address(address);
        address1.setNotes("UPDATED!");
        List<Address> addressList = new ArrayList<Address>();

        Place place1 = new Place();
        place1.setName("MyName");
        place1.setNotes("My updated notes");
        place1.setAddresses(addressList);

        when(placeDao.updatePlace(place1)).thenReturn(place);
        placeService.updatePlace(place1);

        when(placeDao.getPlaceByName("MyNote")).thenReturn(place1);
        Place place2 = placeService.getPlaceByName("MyNote");

        assertEquals(place, place2);
        assertEquals("My updated notes", place2.getNotes());
    }

    @Test
    public void removePlaceTest() throws Exception {
        Place place = new Place();
        place.setName("MyName");
        place.setNotes("MyNotes");

        when(placeDao.getPlaceByName(place.getName())).thenReturn(place);
        Place result = placeService.removePlace(place.getName());

        verify(placeDao, times(1)).getPlaceByName(place.getName());
        verify(placeDao, times(1)).removePlace(place.getName());
        assertEquals(place, result);
    }

    @Test
    public void removeNonExistingPlaceTest() throws Exception {
        Place place = new Place();
        place.setName("MyName");
        place.setNotes("MyNotes");

        when(placeDao.getPlaceByName(place.getName())).thenReturn(null);
        Place result = placeService.removePlace(place.getName());

        verify(placeDao, times(1)).getPlaceByName(place.getName());
        verify(placeDao, times(0)).removePlace(place.getName());
        assertNull(result);
    }

    @Test
    public void updateNonExistingPlaceTest() throws Exception {
        Place place = new Place();
        place.setName("MyName");
        place.setNotes("MyNotes");

        Place place1 = new Place();
        place1.setName("MyName");
        place1.setNotes("My updated notes");

        when(placeDao.updatePlace(place1)).thenThrow(new PlaceDatabaseException("thrown"));
        Place placeUpdated = placeService.updatePlace(place1);

        assertNull(placeUpdated);
    }

    @Test
    public void createEmptyNamePlace() throws Exception {
        Place place = new Place();

        Place place1 = placeService.addPlace(place);
        assertNull(place1);
    }

}

