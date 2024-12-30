package teams.student.astraphobia.units;

import java.util.ArrayList;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.energy.SmallLaser;
import components.weapon.kinetic.FlakBattery;
import components.weapon.kinetic.MachineGun;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import teams.student.astraphobia.*;

public class Fighter extends astraphobiaUnit 
{
	
	public Fighter(astraphobia p)  
	{
		super(p);
	}
	
	public void design()
	{
		setFrame(Frame.MEDIUM);
		setStyle(Style.DAGGER);
		if(usingKinetic = true)
		{
			addWeapon(new FlakBattery(this));
		}
		else 
		{
			addWeapon(new MachineGun(this));
		addWeapon(new SmallLaser(this));	
		}
		addUpgrade(new Plating(this));
		addUpgrade(new Shield(this));
	}

	public void action() 
	{
		skirmish(getWeaponOne());
		skirmish(getWeaponTwo());
		checkWeapon();
	}	

}
