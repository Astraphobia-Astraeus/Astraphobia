package teams.student.astraphobia.units;

import components.upgrade.Plating;
import components.upgrade.Shield;
import components.weapon.Weapon;
import components.weapon.utility.RepairBeam;
import components.weapon.utility.SpeedBoost;
import objects.entity.unit.Frame;
import objects.entity.unit.Style;
import objects.entity.unit.Unit;
import player.Player;
import teams.student.astraphobia.*;

public class Repairerer extends astraphobiaUnit{

	private Unit ally;
	
	private boolean case1 = false;
	private boolean case2 = false;
	private boolean case3 = false;
	
	
	
	public Repairerer(astraphobia p) {
		super(p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void design() {
		// TODO Auto-generated method stub
		
		setFrame(Frame.MEDIUM);
		setStyle(Style.BOXY);
		
		case1 = false;
		case2 = false;
		case3 = false;
		
		addWeapon(new RepairBeam(this));
		addWeapon(new SpeedBoost(this));
		if(usingKinetic)
		{
			addUpgrade(new Shield(this));
		}
		if(usingEnergy)
		{
			addUpgrade(new Plating(this));
		}
		if(usingExplosive)
		{
			addUpgrade(new Shield(this));
		}	
	}
	
	public void action()
	{
		if((case1 || case3) && !case2)
		{
			healAllies(getWeaponOne());
			healAllies(getWeaponTwo());
		}
		else if(!case1 && case2 && !case3)
		{
			healAllies(getWeaponOne());
		}
		else
		{
			healAllies(getWeaponOne());
			healAllies(getWeaponTwo());
		}
	}
	public void healAllies(Weapon w)
	{
		ally = getLowestUnitExclude2(getPlayer(), Gathterers.class, Grub.class, 1000);
		
		if(ally == null)
		{
			ally = nearestUnitExclude2(getPlayer(), Gathterers.class, Grub.class);
		}
		
		if(getDistance(ally) > getMaxRange() * .75f)
		{
			turnTo(ally);
			move();
			w.use();
			case1 = true;
			case2 = false;
			case3 = false;
		}
		if(getDistance(ally) < getMaxRange() * .75f)
		{
			turnTo(getHomeBase());
			move();
			w.use(ally);
			case1 = false;
			case2 = true;
			case3 = false;
		}
		if(this.getCenterX() > ally.getCenterX())
		{
			turnTo(getHomeBase());
			move();
			w.use();
			case1 = false;
			case2 = false;
			case3 = true;
		}	
		if(w == null)
		{
			return;
		}
		
		
	}	
}
