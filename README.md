# scores
An Event Sourcing Exaploration
This project is a collection of Microservices which we're building to explore various architectures and technologies in the realm of event sourcing.

Each Microservice is contained in a separate subdirectory:
* rest-gateway-spring
* league_table_view

## League Table View
Consumes messages from a matchResult kafka topic.  Messages should be in the JSON format:

{
  "competitionId": "<League Name>",
  "homeTeam": "<Team Name>",
  "homeScore": <Score>,
  "awayTeam": "<Team Name>",
  "awayScore": <Score>
}

By default, there is a dummy league created so that integration with the service can be tested.  The dummy league name is 'Dummy League', and contains 2 teams; 'Tottenham Hotspur' and 'Arsenal'.


