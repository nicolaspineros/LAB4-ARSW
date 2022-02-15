/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.filters.RedundancyFilter;
import edu.eci.arsw.blueprints.filters.UndersamplingFilter;
import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }

    @Test
    public void getBlueprintsByAuthorTest(){
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp1=new Blueprint("john", "thepaint",pts1);
        try {
            ibpp.saveBlueprint(bp0);
            ibpp.saveBlueprint(bp1);
        } catch (BlueprintPersistenceException e) {
            e.printStackTrace();
        }
        Set<Blueprint> prueba = new HashSet<>();
        prueba.add(bp0);
        Set<Blueprint> resp=null;
        try {
            resp = ibpp.getBlueprintsByAuthor("mack");
        } catch (BlueprintNotFoundException e) {
            e.printStackTrace();
        }
        assertEquals(prueba,resp);
    }

    @Test
    public void redundancyFilter() {
        RedundancyFilter filtro = new RedundancyFilter();
        Point points[] = {new Point(10,10), new Point(10,10), new Point(20,20), new Point(20,20),new Point(30,30), new Point(30,30)};
        Blueprint bp = new Blueprint("mack","mypsint",points);
        bp = filtro.filter(bp);
        List<Point> resp = new ArrayList<>();
        resp.add(new Point(10,10));
        resp.add(new Point(20,20));
        resp.add(new Point(30,30));
        assertEquals(bp.getPoints().size(), resp.size());
        List<Point> respBp = bp.getPoints();
        for (int i = 0; i < resp.size(); i++){
            /*assertEquals(resp.get(i).getX(),respBp.get(i).getX());
            assertEquals(resp.get(i).getY(),respBp.get(i).getY());*/
            assertTrue(resp.get(i).getX() == respBp.get(i).getX() && resp.get(i).getY() == respBp.get(i).getY());
        }

    }

    @Test
    public void undersasmplingFilter(){
        UndersamplingFilter filtro = new UndersamplingFilter();
        Point points[] = {new Point(10,10), new Point(15,15), new Point(20,20), new Point(25,25),new Point(30,30), new Point(35,35)};
        Blueprint bp = new Blueprint("mack","mypsint",points);
        bp = filtro.filter(bp);
        List<Point> resp = new ArrayList<>();
        resp.add(new Point(10,10));
        resp.add(new Point(20,20));
        resp.add(new Point(30,30));
        assertEquals(bp.getPoints().size(), resp.size());
        List<Point> respBp = bp.getPoints();
        for (int i = 0; i < resp.size(); i++){
            assertTrue(resp.get(i).getX() == respBp.get(i).getX() && resp.get(i).getY() == respBp.get(i).getY());
        }
    }
    
}
