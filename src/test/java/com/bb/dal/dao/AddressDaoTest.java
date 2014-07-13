package com.bb.dal.dao;/**
 * Created with IntelliJ IDEA.
 * User: belozovs
 * Date: 7/7/14
 * Time: 2:07 PM
 * To change this template use File | Settings | File Templates.
 */

import com.bb.dal.entity.Address;
import com.bb.dal.entity.Place;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "classpath*:META-INF/spring/test-context.xml"})
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class AddressDaoTest {

    @PersistenceContext(unitName = "myEntityManager")
    EntityManager em;

    @Autowired
    AddressDao addressDao;
    @Autowired
    PlaceDao placeDao;

    private Place place;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        place = new Place();
        place.setName("myPlace"+System.currentTimeMillis());
        place.setNotes("myNotes");
        place.setAlias("place_"+System.currentTimeMillis());
        placeDao.addPlace(place);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void addUpdateRemoveAddress() throws Exception {

        Address address = new Address();
        address.setAlias("main");
        address.setAddress("street1");
        address.setMetro("station1");
        address.setNotes("note1");
        Place myPlace = placeDao.getPlaceByName(place.getName());
        assertEquals(0, myPlace.getAddresses().size());
        address.setPlace(myPlace);
        addressDao.addAddress(address);
        assertEquals(1, myPlace.getAddresses().size());
        assertEquals(1, addressDao.getAddressesForPlace(myPlace.getId()).size());

        Address address2 = new Address();
        address2.setAlias("additional");
        address2.setAddress("street 2");
        address2.setMetro("station 2");
        address2.setNotes("note 2");
        address2.setPlace(myPlace);
        addressDao.addAddress(address2);
        assertEquals(2, myPlace.getAddresses().size());
        assertEquals(2, addressDao.getAddressesForPlace(myPlace.getId()).size());
        assertNotNull(addressDao.getAddressByAlias("main", myPlace.getId()));
        assertNotNull(addressDao.getAddressByAlias("additional",myPlace.getId()));

        Address address1 = addressDao.getAddressByAlias("main", myPlace.getId());
        address1.setAddress(address1.getAddress()+" modified");
        address1.setMetro(address1.getMetro()+" modified");
        address1.setNotes(address1.getNotes()+" modified");
        addressDao.updateAddressByAlias(address1);
        assertEquals(2, myPlace.getAddresses().size());
        assertEquals(2, addressDao.getAddressesForPlace(myPlace.getId()).size());
        assertTrue(addressDao.getAddressByAlias("main", myPlace.getId()).getAddress().indexOf("modified")!=-1);
        assertTrue(addressDao.getAddressByAlias("main", myPlace.getId()).getMetro().indexOf("modified")!=-1);
        assertTrue(addressDao.getAddressByAlias("main", myPlace.getId()).getNotes().indexOf("modified")!=-1);

        Address address3 = addressDao.getAddressByAlias("additional", myPlace.getId());
        assertNotNull(address3);
        addressDao.removeAddress(address3);
        assertEquals(1, addressDao.getAddressesForPlace(myPlace.getId()).size());

    }


}
