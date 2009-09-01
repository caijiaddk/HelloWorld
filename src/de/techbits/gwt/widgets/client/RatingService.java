package de.techbits.gwt.widgets.client;

import com.google.gwt.user.client.rpc.RemoteService;

/**
 * Defines the service interface for the servlet that provides the rating
 * of an object and stores the new rating when a user has submitted it.
 * 
 * @author Florian Maul (f.maul@sourceforge.net)
 */
public interface RatingService extends RemoteService {
    
    /**
     * Stores the rating of an object.
     * @param theRatedObjectId Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType Custom string, e.g. an object type.
     * @param rating The rating (in number of stars) to be set.
     */
    void setRating(Integer theRatedObjectId, String theRatedObjectType, Integer rating);
    
    /**
     * Retrieves the current rating of an object.
     * @param theRatedObjectId Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType Custom string, e.g. an object type.
     * @return The rating current of the object (in number of stars)
     */
    Integer getRating(Integer theRatedObjectId, String theRatedObjectType); 
}
