package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;

import java.util.ArrayList;
import java.util.List;

//Filtro para suprimir 1 de cada 2 puntos del plano, de manera intercalada.
public class UndersamplingFilter implements BlueprintsFilter{
    @Override
    public Blueprint filter(Blueprint bp){
        List<Point> firstPoints = bp.getPoints();
        ArrayList<Point> finalPoints = new ArrayList<Point>();
        for (int i=0;i<firstPoints.size();i++){
            if (i%2==0){
                finalPoints.add(firstPoints.get(i));
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(),finalPoints);
    }
}
