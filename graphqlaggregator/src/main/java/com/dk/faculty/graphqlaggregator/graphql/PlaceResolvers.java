package com.dk.faculty.graphqlaggregator.graphql;

import com.dk.faculty.graphqlaggregator.beans.PlaceBean;
import com.dk.faculty.graphqlaggregator.entities.Place;
import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.security.annotations.Secure;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLNonNull;
import io.leangen.graphql.annotations.GraphQLQuery;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
@GraphQLClass
@Secure
public class PlaceResolvers {
    @Inject
    private PlaceBean placeBean;

    // queries
    @PermitAll
    @GraphQLQuery
    public List<Place> getPlaces() {
        return placeBean.getPlaces();
    }

    @PermitAll
    @GraphQLQuery
    public Place getPlace(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return placeBean.getPlace(id);
    }

    // mutations
    @RolesAllowed("admin")
    @GraphQLMutation
    public Place addPlace(@GraphQLArgument(name = "place") @GraphQLNonNull Place place) {
        return placeBean.addPlace(place);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public Place editPlace(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id, @GraphQLArgument(name = "place") @GraphQLNonNull Place place) {
        return placeBean.editPlace(id, place);
    }

    @RolesAllowed("admin")
    @GraphQLMutation
    public boolean deletePlace(@GraphQLArgument(name = "id") @GraphQLNonNull Integer id) {
        return placeBean.deletePlace(id);
    }
}
