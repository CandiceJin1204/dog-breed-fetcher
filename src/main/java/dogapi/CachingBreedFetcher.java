package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private BreedFetcher fetcher;
    private Map<String, List<String>> c;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        this.c = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        if (breed == null){
            throw new BreedNotFoundException(breed);
        }
        breed = breed.toLowerCase(Locale.ROOT);
        if (c.containsKey(breed)){
            return c.get(breed);
        }
        callsMade += 1;
        List<String> result = fetcher.getSubBreeds(breed);
        if (result == null || result.isEmpty()){
            c.put(breed, result);
            return result;
        }
        c.put(breed, result);
        return result;
//        return new ArrayList<>();
    }

    public int getCallsMade() {
        return callsMade;
    }
}