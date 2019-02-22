package sample;

import flockbase.Bird;
import flockbase.Flock;
import flockbase.Position;

import java.io.PrintStream;
import java.util.ArrayList;

public class Bird523 extends Bird {
    private int speed = 10;
    private int is_leader;

    public Bird523() {
        super();
    }

    public String getName() {
        if (is_leader == 1)  return "Bird(Leader)";
        else return "Bird";
    }

    public void becomeLeader() {
        is_leader = 1;
    }

    public void retireLead() {
        is_leader = 0;
    }

    double[] calculate_change(int x_target,int y_target){
        Position curr_pos = getPos();
        int x = curr_pos.getX();
        int y = curr_pos.getY();
        double dy;
        double dx;
        if((x_target == x) && (y_target == y)){
            dx = 0.0D;
            dy = 0.0D;
        } 
        else{
            if(x_target == x){
                if (y_target > y) dy = 1.0D;
                else dy = -1.0D;
                dx = 0.0D;
            }
            else{
                if(y_target == y){
                    if (x_target > x) dx = 1.0D;
                    else dx = -1.0D;
                    dy = 0.0D;
                } 
                else{
                    double m = (y_target - y) / (x_target - x);
                    if (m > 50.0) m = 50.0;
                    if (m < -50.0) m = -50.0;
                    if (x_target > x) dx = 1.0D;
                    else dx = -1.0D;
                    dy = m * dx;
                    dx *= speed;
                }
            }
        }
        double[] x_y_change = new double[2];
        x_y_change[0] = dx;
        x_y_change[1] = dy;
        return x_y_change;
    }

    protected void updatePos() {
        Position curr_pos = getPos();
        int x = curr_pos.getX();
        int y = curr_pos.getY();
        Flock flock = this.getFlock();
        ArrayList<Bird> flock_members = flock.getBirds();
        if (is_leader == 0) {
            Position leader_position = flock.getLeader().getPos();
            setTarget(leader_position.getX(), leader_position.getY());
        }
        int xt = getTarget().getX();
        int yt = getTarget().getY();
        double[] x_y_change = calculate_change(xt, yt);
        Position new_position = new Position(0, 0);
        for(int i=0;i<flock_members.size();i++){
            if(flock_members.get(i) != this){
                if(Math.sqrt(Math.pow((getPos().getX() - flock_members.get(i).getPos().getX()), 2) + Math.pow((getPos().getY() - flock_members.get(i).getPos().getY()), 2)) < 20 ){
                    int sub_x = flock_members.get(i).getPos().getX() - getPos().getX();
                    int sub_y = flock_members.get(i).getPos().getY() - getPos().getY();
                    new_position.setPos(new_position.getX()-sub_x,new_position.getY()-sub_y);
                }
            }
        }
        setPos(x + new_position.getX()+ (int) x_y_change[0], y  + new_position.getY()+(int) x_y_change[1]);
    }
}
