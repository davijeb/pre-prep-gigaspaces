package com.gigaspaces.prep;

import com.gigaspaces.DataGridConnectionUtility;
import com.gigaspaces.prep.models.Person;
import com.j_spaces.core.client.SQLQuery;
import org.openspaces.admin.Admin;
import org.openspaces.admin.AdminFactory;
import org.openspaces.admin.gsm.GridServiceManager;
import org.openspaces.core.GigaSpace;

public class GigaMain {

    public static void main(String[] args) {

        //Admin admin = new AdminFactory().createAdmin();

        //GridServiceManager esm = admin.getGridServiceManagers().waitForAtLeastOne();
        //ProcessingUnit pu = esm.deploy(new SpaceDeployment("thespace").partitioned(2, 1));

        //pu.waitFor(4, 30, TimeUnit.SECONDS);

        GigaSpace gs = DataGridConnectionUtility.getSpace("ubs_datagrid");

        //GigaSpace gigaSpace = pu.waitForSpace().getGigaSpace();

        gs.write(new Person(1, "Jeremy", "Davies"));
        gs.write(new Person(2, "Lucy", "Davies"));


        //read by ID
        Person vince = gs.readById(Person.class, 1);

        //read with SQL query
        Person johny = (Person) gs.read(new SQLQuery(Person.class, "firstName=?", "Jeremy"));

        System.out.println(johny);

        //readMultiple with template
        Person[] vinceAndJohny = gs.readMultiple(new Person());


    }
}
