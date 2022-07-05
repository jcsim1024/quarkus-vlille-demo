package org.acme.vlille.vlille.api;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockExtensions implements QuarkusTestResourceLifecycleManager {

    private WireMockServer wireMockServer;

    @Override
    public Map<String, String> start() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        stubFor(
                get(
                        urlMatching(".*")
                )
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(RAW_DATASET)));

        return Collections.singletonMap("org.acme.vlille.vlille.api.RawVlilleServiceRestEasy/mp-rest/url", wireMockServer.baseUrl());
    }

    @Override
    public void stop() {
        if (Objects.nonNull(wireMockServer))
            wireMockServer.stop();
    }

    private static final String RAW_DATASET = """
            {
               "nhits":254,
               "parameters":{
                  "dataset":"vlille-realtime",
                  "rows":10,
                  "start":0,
                  "format":"json",
                  "timezone":"UTC"
               },
               "records":[
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"fdd36680ce4f06b3c7eeb59f0c7b76d40def9fbe",
                     "fields":{
                        "nbvelosdispo":16,
                        "nbplacesdispo":9000,
                        "libelle":110,
                        "adresse":"11 RUE BONTE POLLET",
                        "nom":"RUE BONTE-POLLET",
                        "etat":"EN SERVICE",
                        "commune":"LILLE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.62487,
                           3.034234
                        ],
                        "localisation":[
                           50.62487,
                           3.034234
                        ],
                        "datemiseajour":"2022-07-05T15:58:18+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.034234,
                           50.62487
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"2f98f2e1ee73e414cf64bae428caa96ba114be23",
                     "fields":{
                        "nbvelosdispo":5,
                        "nbplacesdispo":14,
                        "libelle":30,
                        "adresse":"8 PLACE DE GAND",
                        "nom":"RUE DE GAND",
                        "etat":"EN SERVICE",
                        "commune":"LILLE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.64297,
                           3.067375
                        ],
                        "localisation":[
                           50.64297,
                           3.067375
                        ],
                        "datemiseajour":"2022-07-05T15:58:17+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.067375,
                           50.64297
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"256e3162a750634ac072e7f14a5b0149693581de",
                     "fields":{
                        "nbvelosdispo":1,
                        "nbplacesdispo":17,
                        "libelle":35,
                        "adresse":"24 PLACE MARECHAL LECLERC",
                        "nom":"LECLERC",
                        "etat":"EN SERVICE",
                        "commune":"LILLE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.62899,
                           3.043307
                        ],
                        "localisation":[
                           50.62899,
                           3.043307
                        ],
                        "datemiseajour":"2022-07-05T15:58:17+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.043307,
                           50.62899
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"7d59e538ad770fce77d8dd767daf4b1f4fcaeab4",
                     "fields":{
                        "nbvelosdispo":14,
                        "nbplacesdispo":10,
                        "libelle":100,
                        "adresse":"260 AVENUE DE LA REPUBLIQUE",
                        "nom":"SAINT MAUR",
                        "etat":"EN SERVICE",
                        "commune":"LA MADELEINE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.651638,
                           3.081625
                        ],
                        "localisation":[
                           50.651638,
                           3.081625
                        ],
                        "datemiseajour":"2022-07-05T15:58:18+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.081625,
                           50.651638
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"8b60cc95eaeff78a519a9ea4282b3a09f2c12ccc",
                     "fields":{
                        "nbvelosdispo":0,
                        "nbplacesdispo":16,
                        "libelle":102,
                        "adresse":"AVENUE ROBERT SCHUMAN",
                        "nom":"MAIRIE DE MONS",
                        "etat":"EN SERVICE",
                        "commune":"MONS EN BAROEUL",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.64218,
                           3.109699
                        ],
                        "localisation":[
                           50.64218,
                           3.109699
                        ],
                        "datemiseajour":"2022-07-05T15:58:18+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.109699,
                           50.64218
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"f71c6e9781b180d5fa36b18a15a2b201b868f817",
                     "fields":{
                        "nbvelosdispo":11,
                        "nbplacesdispo":13,
                        "libelle":144,
                        "adresse":"FACE AU 1 BIS RUE DE LA STATION",
                        "nom":"ANNAPPES REPUBLIQUE",
                        "etat":"EN SERVICE",
                        "commune":"VILLENEUVE D'ASCQ",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.6258,
                           3.14862
                        ],
                        "localisation":[
                           50.6258,
                           3.14862
                        ],
                        "datemiseajour":"2022-07-05T15:58:18+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.14862,
                           50.6258
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"4c82424545ef1eb11a3aad1889c4cda98ef587e2",
                     "fields":{
                        "nbvelosdispo":2,
                        "nbplacesdispo":14,
                        "libelle":147,
                        "adresse":"2 RUE MARCEL H\\u00c9NAUX",
                        "nom":"FAUBOURG D'ARRAS",
                        "etat":"EN SERVICE",
                        "commune":"LILLE",
                        "etatconnexion":"CONNECTED",
                        "type":"SANS TPE",
                        "geo":[
                           50.614052,
                           3.062195
                        ],
                        "localisation":[
                           50.614052,
                           3.062195
                        ],
                        "datemiseajour":"2022-07-05T15:58:18+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.062195,
                           50.614052
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"6d540b30f23f2b2d9d30729218991d2f432db89b",
                     "fields":{
                        "nbvelosdispo":0,
                        "nbplacesdispo":16,
                        "libelle":41,
                        "adresse":"681 AVENUE DE LA REPUBLIQUE",
                        "nom":"BUISSON",
                        "etat":"EN SERVICE",
                        "commune":"LILLE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.656487,
                           3.088004
                        ],
                        "localisation":[
                           50.656487,
                           3.088004
                        ],
                        "datemiseajour":"2022-07-05T15:58:17+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.088004,
                           50.656487
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"5647b7a4c5b34682ef86a7d36291466dcc577952",
                     "fields":{
                        "nbvelosdispo":11,
                        "nbplacesdispo":7,
                        "libelle":44,
                        "adresse":"199 RUE LEON GAMBETTA",
                        "nom":"GAMBETTA UTRECHT",
                        "etat":"EN SERVICE",
                        "commune":"LILLE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.629063,
                           3.053711
                        ],
                        "localisation":[
                           50.629063,
                           3.053711
                        ],
                        "datemiseajour":"2022-07-05T15:58:17+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.053711,
                           50.629063
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  },
                  {
                     "datasetid":"vlille-realtime",
                     "recordid":"d5c1c6c9492c234e99eb016bc1ac69c0b7b7aa0f",
                     "fields":{
                        "nbvelosdispo":9,
                        "nbplacesdispo":15,
                        "libelle":153,
                        "adresse":"FACE AU 102 ALLEE DU RIEZ",
                        "nom":"POMPIDOU",
                        "etat":"EN SERVICE",
                        "commune":"LA MADELEINE",
                        "etatconnexion":"CONNECTED",
                        "type":"AVEC TPE",
                        "geo":[
                           50.65547,
                           3.064989
                        ],
                        "localisation":[
                           50.65547,
                           3.064989
                        ],
                        "datemiseajour":"2022-07-05T15:58:18+00:00"
                     },
                     "geometry":{
                        "type":"Point",
                        "coordinates":[
                           3.064989,
                           50.65547
                        ]
                     },
                     "record_timestamp":"2022-07-05T16:05:01.775Z"
                  }
               ]
            }
            """;
}
