package brickset;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import repository.Repository;

/**
 * Represents a repository of {@code LegoSet} objects.
 */
public class LegoSetRepository extends Repository<LegoSet> {

    public LegoSetRepository() {
        super(LegoSet.class, "brickset.json");
    }

    /**
     * True értéket ad vissza ha létezik Duplo Theme a LegoSetben.
     *
     * @return true ha létezik Duplo Theme a LegoSetben.
     */
    public boolean existsDuploLegoSetTheme (){
        return getAll().stream().anyMatch(legoSet -> legoSet.getTheme().equals("Duplo"));
    }

    /**
     * Minden LegoSet nevének a kezdőbetűjét írja ki egy sorban egymas mellé
     */
    public void printLegoSetNamesFirstCharacters (){
        getAll().stream().flatMap(legoSet -> Stream.of(legoSet.getName().charAt(0))).forEach(System.out::print);
    }

    /**
     *  Visszaadja a lego darabjainak összegét.
     *
     * @return a lego darabjainak összegét.
     */
    public int countAllLegoSetPieces (){
        return getAll().stream().mapToInt(LegoSet::getPieces)
                .reduce(0, Integer::sum);
    }

    /**
     * Visszatér egy Mapel aminek a kulcsa a LegoSet Number, értéke pedig a LegoSet neve.
     *
     * @return Map<String, String> aminek a kulcsa a LegoSet Number, értéke pedig a LegoSet neve.
     */
    public Map<String, String> createMapWithLegoSetNumberAndName (){
        return getAll().stream().collect(Collectors.toMap(LegoSet::getNumber, LegoSet::getName));
    }

    /**
     * Visszatér egy Mapel aminek a kulcsa egy boolean, az értéke pedig egy szam.
     *
     * @return Map<Boolean, Long> aminek a kulcsa egy boolean, értéke pedig annak a darabszama amennyiszer
     * nem null a LegoSet SubTheme mezője.
     */
    public Map<Boolean, Long> createMapWithBooleanAndLegoSetSubThemeExisting(){
        return getAll().stream().collect(Collectors.partitioningBy(
                legoSet -> legoSet.getSubtheme() != null, Collectors.counting()));
    }

    public static void main(String[] args) {
        var repository = new LegoSetRepository();

        //1
        System.out.println("Letezik Duplo Theme Legoset: "+repository.existsDuploLegoSetTheme());
        //2
        System.out.println("Lego neveinek a kezdőbetűjük:");
        repository.printLegoSetNamesFirstCharacters();
        System.out.println();
        //3
        System.out.println(repository.countAllLegoSetPieces()+ "db lego darab van összesen.");
        //4
        System.out.println(repository.createMapWithLegoSetNumberAndName());
        //5
        System.out.println(repository.createMapWithBooleanAndLegoSetSubThemeExisting());
    }

}
