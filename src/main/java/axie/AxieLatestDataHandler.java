package axie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class AxieLatestDataHandler {

    private static final Configuration configuration = new Configuration();
    /*
        Changes received JSON into AxieModel
     */

    public static List<AxieModel> processAxies(int axiesToLoad) throws Exception {

        // Body from config file
        String jsonBody = "{\n" +
                "        \"operationName\": \"GetAxieLatest\",\n" +
                "        \"variables\": {\n" +
                "        \"from\": 0,\n" +
                "        \"size\": 10,\n" +
                "        \"sort\": \"Latest\",\n" +
                "        \"auctionType\": \"Sale\"\n" +
                "        },\n" +
                "        \"query\": \"query GetAxieLatest($auctionType: AuctionType, $criteria: AxieSearchCriteria, $from: Int, $sort: SortBy, $size: Int, $owner: String) {\\n  axies(auctionType: $auctionType, criteria: $criteria, from: $from, sort: $sort, size: $size, owner: $owner) {\\n    total\\n    results {\\n      ...AxieRowData\\n      __typename\\n    }\\n    __typename\\n  }\\n}\\n\\nfragment AxieRowData on Axie {\\n  id\\n  image\\n  class\\n  name\\n  genes\\n  owner\\n  class\\n  stage\\n  title\\n  breedCount\\n  level\\n  parts {\\n    ...AxiePart\\n    __typename\\n  }\\n  stats {\\n    ...AxieStats\\n    __typename\\n  }\\n  auction {\\n    ...AxieAuction\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment AxiePart on AxiePart {\\n  id\\n  name\\n  class\\n  type\\n  specialGenes\\n  stage\\n  abilities {\\n    ...AxieCardAbility\\n    __typename\\n  }\\n  __typename\\n}\\n\\nfragment AxieCardAbility on AxieCardAbility {\\n  id\\n  name\\n  attack\\n  defense\\n  energy\\n  description\\n  backgroundUrl\\n  effectIconUrl\\n  __typename\\n}\\n\\nfragment AxieStats on AxieStats {\\n  hp\\n  speed\\n  skill\\n  morale\\n  __typename\\n}\\n\\nfragment AxieAuction on Auction {\\n  startingPrice\\n  endingPrice\\n  startingTimestamp\\n  endingTimestamp\\n  duration\\n  timeLeft\\n  currentPrice\\n  currentPriceUSD\\n  suggestedPrice\\n  seller\\n  listingIndex\\n  state\\n  __typename\\n}\\n\"\n" +
                "        }";

        // Load JSON data from URL
        JSONObject data = AxieDataRequester.getAxiesJSONObject(jsonBody);

        // Group objects by JSON
        JSONObject axies = data.getJSONObject("axies");

        JSONArray axieData = axies.getJSONArray("results");

        List<AxieModel> axieModelList = new ArrayList<>();

        // Call AxieModel
        AxieModel axie;

       for (int dataIndex = 0; dataIndex < axiesToLoad; dataIndex++) {

           for (int x = 0; x < 6; x++) {

               // Prepare list for abilities
               List<String> abilitiesList = new ArrayList<>();

               // Load abilities into List, each Axie have 6 abilities
               for (int abilityIndex = 0; abilityIndex < 6; abilityIndex++) {
                   abilitiesList.add(axieData.getJSONObject(dataIndex).getJSONArray("parts").getJSONObject(abilityIndex).getString("name"));
               }

               // Prepare the rest of data
               Object type = axieData.getJSONObject(dataIndex).get("class");
               String name = axieData.getJSONObject(dataIndex).getString("name");
               String image = axieData.getJSONObject(dataIndex).getString("image");

               double price = Double.parseDouble(new DecimalFormat("##.##").format(axieData.getJSONObject(dataIndex).getJSONObject("auction").getFloat("currentPriceUSD")));

               int breedCount = axieData.getJSONObject(dataIndex).getInt("breedCount");
               int id = axieData.getJSONObject(dataIndex).getInt("id");
               //


               // Some axies have null type
               if (type != null) {

                   // Create an instance of AxieModel and add data
                   axie = new AxieModel(
                           id, name, image, String.valueOf(type), breedCount, price, abilitiesList
                   );

               } else {

                   axie = new AxieModel(
                           id, name, image, "none", breedCount, price, abilitiesList
                   );

               }

               // Add axies to the list
               axieModelList.add(axie);

           }

       }

       return axieModelList;
    }

}
