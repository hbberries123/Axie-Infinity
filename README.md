# Axie Infinity Market
Load and display recently listed Axies from [Axie Infinity Market](https://marketplace.axieinfinity.com/) in your discord server. I have seen a lot of people having trouble with Axie Infinity Market since it does not provide any API documentation so I decided to create a simple bot that does all the work.

  You can display Axies with command `/axies-recently-listed` and filter them out by `price`, `abilities`, `body parts`.
  
  The application will find new Axies every `5 seconds` and send them to the previously selected discord channel.
  
## Requesting data
To simply receive listed Axies send `POST https://graphql-gateway.axieinfinity.com/graphql` with JSON body:
  ```
{
    "operationName": "GetAxieLatest",
    "variables": {
        "from": 0,
        "size": 10,
        "sort": "Latest",
        "auctionType": "Sale"
    },
    "query": "query GetAxieLatest($auctionType: AuctionType, $criteria: AxieSearchCriteria, $from: Int, $sort: SortBy, $size: Int, $owner: String) 
    {  axies(auctionType: $auctionType, criteria: $criteria, from: $from, sort: $sort, size: $size, owner: $owner) {\n    total\n    results {\n      ...AxieRowData\n      __typename\n    }\n    __typename\n  }\n}\n\nfragment AxieRowData on Axie {\n  id\n  image\n  class\n  name\n  genes\n  owner\n  class\n  stage\n  title\n  breedCount\n  level\n  parts {\n    ...AxiePart\n    __typename\n  }\n  stats {\n    ...AxieStats\n    __typename\n  }\n  auction {\n    ...AxieAuction\n    __typename\n  }\n  __typename\n}\n\nfragment AxiePart on AxiePart {\n  id\n  name\n  class\n  type\n  specialGenes\n  stage\n  abilities {\n    ...AxieCardAbility\n    __typename\n  }\n  __typename\n}\n\nfragment AxieCardAbility on AxieCardAbility {\n  id\n  name\n  attack\n  defense\n  energy\n  description\n  backgroundUrl\n  effectIconUrl\n  __typename\n}\n\nfragment AxieStats on AxieStats {\n  hp\n  speed\n  skill\n  morale\n  __typename\n}\n\nfragment AxieAuction on Auction {\n  startingPrice\n  endingPrice\n  startingTimestamp\n  endingTimestamp\n  duration\n  timeLeft\n  currentPrice\n  currentPriceUSD\n  suggestedPrice\n  seller\n  listingIndex\n  state\n  __typename\n}\n"
}
```
##
![](https://i.imgur.com/fjd0wf0.png)
