package sample;

import flockbase.Bird;
import flockbase.Flock;

import java.util.ArrayList;

public class Flock523 extends flockbase.Flock {

    private ArrayList<Bird> flock_birds = new ArrayList<Bird>();
    Bird flock_leader;

    public Flock523() {
        super();
    }

    public void addBird(Bird b) {
        b.setFlock(this);
        flock_birds.add(b);
    }

    public void removeBird(Bird b) {
        flock_birds.remove(b);
    }

    public void setLeader(Bird leader) {
        if (flock_leader != null) flock_leader.retireLead();
        leader.becomeLeader();
        flock_leader = leader;
    }

    public ArrayList<Bird> getBirds() {
        return flock_birds;
    }

    public Bird getLeader() {
        return flock_leader;
    }

    public flockbase.Flock split(int pos) {
        if(flock_birds.get(pos) == flock_leader){
            flock_leader.retireLead();
            if(pos<flock_birds.size()-1){
                flock_leader = flock_birds.get(pos+1);
                flock_leader.becomeLeader();
            }
            else{
                flock_leader = flock_birds.get(pos-1);
                flock_leader.becomeLeader();
            }
        }
        Bird new_flock_leader = flock_birds.get(pos);
        new_flock_leader.becomeLeader();
        Flock new_flock = new Flock523();
        new_flock.addBird(new_flock_leader);
        new_flock.setLeader(new_flock_leader);
        for(int i=0;i<pos;i++) new_flock.addBird(flock_birds.get(i));
        for(int i=0;i<=pos;i++) {
            if(flock_birds.get(i) != flock_leader) removeBird(flock_birds.get(i));
            pos--;
        }    
        return new_flock;
    }

    public void joinFlock(flockbase.Flock f) {
        getLeader().retireLead();
        for(int i=0;i<flock_birds.size();i++) {
            f.addBird(flock_birds.get(i));
        }
        while(flock_birds.size()>0) removeBird(flock_birds.get(0));
    }
}
