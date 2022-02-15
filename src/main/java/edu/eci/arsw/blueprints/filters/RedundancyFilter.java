package edu.eci.arsw.blueprints.filters;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

//Filtro para suprimir del plano los puntos consecutivos que sean repetidos.

@Service("RedundancyFilter")
public class RedundancyFilter implements BlueprintsFilter {
    @Override
    public Blueprint filter(Blueprint bp){
        ArrayList<Point> points = new ArrayList<Point>();
        for (Point i : bp.getPoints()){
            boolean redundancy = false;
            for (Point j : points){
                if (i.getX() == j.getX() && i.getY() == j.getY()){
                    redundancy=true;
                    break;
                }
            }
            if (!redundancy)points.add(i);
        }
        return new Blueprint(bp.getAuthor(), bp.getName(),points);
    }
}
