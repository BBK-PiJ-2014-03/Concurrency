import java.util.Date;  
import java.util.StringTokenizer;  
import java.util.Random; 
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ImmutableExample
{
    public static void main(String[] args)
    {
        String name = DataGenerator.getNextName();
        Date dateOfBirth = DataGenerator.getNextDate();
        BufferedImage photo = DataGenerator.getNextPhoto();
            
        ImmutableIDCard id = new ImmutableIDCard(name,dateOfBirth,photo);

        Runnable officer = new Officer(id);
        Runnable citizen = new Citizen(id);

        Thread t1 = new Thread(officer);
        Thread t2 = new Thread(citizen);
                  
        t1.start();
        t2.start();
    }
}

class DataGenerator
{
    private static StringTokenizer names = new StringTokenizer("John,Mary,Lisa",",");
    private static StringTokenizer dates = new StringTokenizer("23/6/1971,12/3/1985,9/8/2002",",");

    public synchronized static String getNextName()
    {
        if (names.hasMoreElements())
        {
            return (String)names.nextElement();
        }
            
        return "Not a name";
    }
      
    public synchronized static Date getNextDate()
    {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String txtDate="1/1/1970";
            
        if(dates.hasMoreElements())
        {
            txtDate=(String)dates.nextElement();
        }

        try
        {
            return dateFormat.parse(txtDate);
        }
        catch (ParseException e) 
        {
            return new Date();
	  }            
    }
      
    public synchronized static BufferedImage getNextPhoto()
    {
        return new BufferedImage(30, 30, BufferedImage.TYPE_INT_RGB);
    }
}

class ImmutableIDCard
{
    private final String name;
    private final Date dateOfBirth;
    private final BufferedImage photo;

    public ImmutableIDCard(String name,Date dateOfBirth,BufferedImage photo)
    {
        this.name=name;
        this.dateOfBirth=dateOfBirth;
        this.photo=photo;
    }
      
    public synchronized String getName()
    {
        return new String(name);
    }

    public synchronized Date getDateOfBirth()
    {
        return new Date(dateOfBirth.getTime());
    }

    public synchronized BufferedImage getPhoto()
    {
        return new BufferedImage(photo.getColorModel(), 
                                 photo.copyData(null),
                                 photo.isAlphaPremultiplied(), 
                                 null);
    }
      
    private long getAge(Date dateOfBirth)
    {
        long now = new Date().getTime();
        long dobCopy = new Long(dateOfBirth.getTime());
        long age = now - dobCopy;
        return age;
    }
}

class Citizen implements Runnable
{
    private ImmutableIDCard id;
      
    public Citizen(ImmutableIDCard id)
    {
        this.id = id;
    }
      
    public void run()
    {
        Random r = new Random();

        try
        {
            Thread.sleep(r.nextInt(2000));
        }
        catch (InterruptedException e)
	  {
		e.printStackTrace();
	  }

        String message = "";
        message += "the name on this id is "+id.getName()+"\n";
        message += "and the date of birth is "+id.getDateOfBirth();
            
        System.out.println(message);
    }
}

class Officer implements Runnable
{
    private ImmutableIDCard id;
      
    public Officer(ImmutableIDCard id)
    {
        this.id=id;
    }
      
    public void run()
    {
        Random r=new Random();

        try
        {
            Thread.sleep(r.nextInt(2000));
        }
        catch (InterruptedException e)
	  {
		e.printStackTrace();
	  }
            
        String name = DataGenerator.getNextName();
        Date dateOfBirth = DataGenerator.getNextDate();
        BufferedImage photo = DataGenerator.getNextPhoto();
            
        id = new ImmutableIDCard(name, dateOfBirth, photo);
    }
}
