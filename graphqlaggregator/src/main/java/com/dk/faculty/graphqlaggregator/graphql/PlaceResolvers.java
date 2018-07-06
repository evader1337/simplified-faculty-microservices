package com.dk.faculty.graphqlaggregator.graphql;

import com.dk.faculty.graphqlaggregator.beans.PlaceBean;
import com.dk.faculty.graphqlaggregator.entities.Place;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@GraphQLClass
public class PlaceResolvers {
    @Inject
    private PlaceBean placeBean;

    // queries
    @GraphQLQuery
    public List<Place> getPlaces() {
        return placeBean.getPlaces();
    }

    @GraphQLQuery
    public Place getPlace(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return placeBean.getPlace(id);
    }

    // mutations
    @GraphQLMutation
    public Place addPlace(@GraphQLArgument(name="place") @GraphQLNonNull Place place) {
        return placeBean.addPlace(place);
    }
}
