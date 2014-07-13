package com.bb.dal.dao;/**
 * Created with IntelliJ IDEA.
 * User: belozovs
 * Date: 7/2/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import org.junit.*;

import static junit.framework.Assert.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/spring/test-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class PlaceDaoTest {

    @PersistenceContext(unitName = "myEntityManager")
    EntityManager em;

    @Autowired
    PlaceDao placeDao;

    private static final String PLACE_NAME  = "Kremlin"+System.currentTimeMillis();
    private static final String PLACE_NOTES = "Come and see for free";


    private Address address;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        address = createAddress();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getAllPlacesTest() throws Exception {
        List<Place> allPlaces = placeDao.getAllPlaces();
        assertNotNull(allPlaces);
    }

    @Test
    public void createUpdateDeletePlaceTest() throws Exception {

        int initialRows = placeDao.getAllPlaces().size();

        Place myPlace = new Place();
        myPlace.setName(PLACE_NAME);
        myPlace.setNotes(PLACE_NOTES);
        address.setPlace(myPlace);
        myPlace.addAddress(address);
        myPlace.setAlias("place_"+System.currentTimeMillis());
        placeDao.addPlace(myPlace);

        Place placeToGo = placeDao.getPlaceByName(PLACE_NAME);
        assertNotNull(placeToGo);
        assertEquals(placeToGo.getName(),PLACE_NAME);
        assertEquals(placeToGo.getNotes(),PLACE_NOTES);
        System.out.println(">>>>>>>>>>>>>>>>"+placeToGo.getId());

        String updateString="... Or almost for free";
        placeToGo.setNotes(placeToGo.getNotes()+updateString);
        placeDao.updatePlace(placeToGo);
        Place placeToGoAgain = placeDao.getPlaceByName(PLACE_NAME);
        assertEquals(placeToGoAgain.getName(),PLACE_NAME);
        assertEquals(placeToGoAgain.getNotes(),PLACE_NOTES+updateString);
        assertEquals(placeToGo.getId(),placeToGoAgain.getId());

        assertEquals(placeDao.getAllPlaces().size(), initialRows+1);
        Place placeDoesNotExist=new Place();
        placeDoesNotExist.setName("Nothing");
        placeDao.removePlace(placeDoesNotExist.getName());
        assertEquals(placeDao.getAllPlaces().size(), initialRows+1);
        placeDao.removePlace(placeToGoAgain.getName());
        assertEquals(placeDao.getAllPlaces().size(), initialRows);
    }

    @Test
    public void getByPatternTest() throws Exception {
        int initialSize = placeDao.getPlacesByPattern("rem").size();
        Place myPlace = new Place();
        myPlace.setName(PLACE_NAME);
        myPlace.setNotes(PLACE_NOTES);
        address.setPlace(myPlace);
        myPlace.addAddress(address);
        myPlace.setAlias("place_"+System.currentTimeMillis());
        placeDao.addPlace(myPlace);

        Place place2 = new Place();
        place2.setName("foo");
        place2.setNotes("boo");
        place2.setAlias("place_"+System.currentTimeMillis());
        placeDao.addPlace(place2);

        List<Place> places = placeDao.getPlacesByPattern("rem");
        assertNotNull(places);
        assertEquals(initialSize+1, places.size());

    }

    private Address createAddress(){
        Address address = new Address();
        address.setAddress("street");
        address.setMetro("station");
        address.setNotes("test address");
        address.setAlias("main");
        return address;
    }

}
