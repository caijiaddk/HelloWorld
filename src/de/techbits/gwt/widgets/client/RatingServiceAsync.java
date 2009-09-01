package de.techbits.gwt.widgets.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Defines the async service interface for the servlet that provides the rating
 * of an object and stores the new rating when a user has submitted it.
 * 
 * @author Florian Maul (f.maul@sourceforge.net)
 */
@SuppressWarnings("unchecked")
public interface RatingServiceAsync {

    /**
     * Stores the rating of an object.
     * @param theRatedObjectId Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType Custom string, e.g. an object type.
     * @param rating The rating (in number of stars) to be set.
     * @param callback The async callback object.
     */
	void setRating(Integer theRatedObjectId, String theRatedObjectType, Integer rating, AsyncCallback callback);
    

    /**
     * Retrieves the current rating of an object.
     * @param theRatedObjectId Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType Custom string, e.g. an object type.
     * @param callback The async callback object.
     */
    void getRating(Integer theRatedObjectId, String theRatedObjectType, AsyncCallback callback);  
}
