package Model;


public class CityList {

    public int id;
    public String name;
    public String country;
    public Coord coord;

    public CityList(int id, String name, String country, Coord coord){
        this.id = id;
        this.name = name;
        this.country = country;
        this.coord = coord;
    }

}
