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
RdbReader<Food> reader = new RdbReader<Food>(inputStream, Food.class);
Food food;
while ((food = reader.read()) != null) {
	//do something with the object here
}
reader.close();
```

## Example class

Naming fields on your class works like GSON.  RDB-Converter will match fields by their name or with the `SerializedName` annotation, like in the example below.

```Java
public class Food {

    public String name;

    @SerializedName("del_rtng")
    public BigDecimal deliciousnessRating;

}
``` 

## Limitations

Here are a few limits that may affect your usage:

* only works for a Retrofit call with a `List<Foo>` type
* only works for `String` and `BigDecimal` fields. It's trivial to add others, or make it extensible, but that's all I needed for now
* it only handles deserialization (i.e. InputStream -> Java Object, not the other way around)

## Including in your project

gradle compile statement