# RDB Converter

Deserialize a list of objects from [RDB format](https://pubs.usgs.gov/of/2003/ofr03123/6.4rdb_format.pdf).  Plays nicely with [Retrofit](http://square.github.io/retrofit/), but can also be used alone.

## Using with Retrofit

```Java
new Retrofit.Builder()
	.addConverterFactory(new RdbConverterFactory())
	.build();
```

## Using alone

```Java
RdbReader<Food> reader = new RdbReader<Food>(is, Food.class);
Food food;
while ((food = reader.read()) != null) {
	//do something with the object here
}
reader.close();
```

## Example class

Naming fields on your class works like GSON.  RDB-Converter will match field names, or names assigned with the `SerializedName` annotation, like in the example below.

```Java
public class Food {

    public String name;

    @SerializedName("del_rtng")
    public BigDecimal deliciousnessRating;

}
``` 

## Limitations

only List<Foo>, 
only String and BigDecimal
only deserialization

If any of these things is a problem for your usage, file an Issue.  Better yet, add what you need and make a PR.  

## Including in your project

gradle compile statement