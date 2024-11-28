package teams.student.astraphobia.units;

import java.util.ArrayList;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.upgrade.Structure;
import components.weapon.Weapon;
import components.weapon.energy.SmallLaser;
import components.weapon.kinetic.MachineGun;
import components.weapon.kinetic.Railgun;
import components.weapon.resource.Collector;
import components.weapon.resource.MiningLaser;
import components.weapon.utility.RepairBeam;
import components.weapon.utility.ShieldBattery;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.astraphobia.*;

public class Healer extends astraphobiaUnit 
{
	public boolean s1 = false;
	public boolean p1 = false;
	public boolean st1 = false;
	public boolean s_e = false;
	public boolean m_m = false;
	ArrayList<Unit> allys = this.getAllies();
	public Healer(astraphobia p)  
	{
		super(p);
	}

	public void design()
	{
		setFrame(Frame.HEAVY);
		setStyle(Style.DAGGER);
		checkWeapon();
		checkUpgrade();

		if(usingKinetic)
		{
			s1 = true;
			p1 = false;
			st1 = false;
			addUpgrade(new Shield(this));
		}
		else if(usingEnergy)
		{		
			p1 = true;
			s1 = false;
			st1 = false;
			addUpgrade(new Plating(this));
		}
		else
		{
			st1 = true;
			p1 = false; 
			s1 = false;
			addUpgrade(new Structure(this));
		}
		addWeapon(new ShieldBattery(this));
		addWeapon(new RepairBeam(this));
	}


	public void action() 
	{
		skirmish(getWeaponOne());
		skirmish(getWeaponTwo());
	}	
	public void skirmish(Weapon w)
	{
		Unit fast = getNearestAlly(Fast.class);		
		if(fast != null) {
			w.use(fast);	
			
			if((getDistance(fast) > getMaxRange()) )
			{
				moveTo(fast);
			}
			else
			{ 
				turnTo(fast);
				turnAround();
				move(35);
			}
		}
	}
}

