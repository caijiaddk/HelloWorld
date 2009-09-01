package de.techbits.gwt.widgets.client;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * StarRating is a widget that creates an interactive rating control.
 * 
 * @author Florian Maul (f.maul@sourceforge.net)
 */
@SuppressWarnings({"unchecked","deprecation"})
public class StarRating extends Widget {

    /**
     * Private subclass that represents a single star in the rating widget.
     * 
     * @author Florian Maul (f.maul@sourceforge.net)
     */
    private class StarImage extends Widget {

        /**
         * Stores if the star is currently highlighted by the user (mouseover).
         */
        private boolean highlighted = false;

        /**
         * Stores if the star is currently activated (marked as active).
         */
        private boolean active = false;

        /**
         * Sets the current star as activated and updates it's appearance.
         * 
         * @param isActive
         *            a bool that indicates whether the star is active.
         */
        public void setActive(final boolean isActive) {
            if (this.active != isActive) {
                this.active = isActive;
                updateImage();
            }
        }

        /**
         * Sets the current star as highlighted and updates it's appearance.
         * 
         * @param isHighlighted
         *            a bool that indicates whether the star is highlighted.
         */
        public void setHighlighted(final boolean isHighlighted) {
            if (this.highlighted != isHighlighted) {
                this.highlighted = isHighlighted;
                updateImage();
            }
        }

        /**
         * Updates the appearance (css style class) of the star.
         */
        private void updateImage() {
            if (active) {
                if (highlighted) {
                    setStyleName("starrating-active-marked");
                } else {
                    setStyleName("starrating-active-unmarked");
                }
            } else {
                if (highlighted) {
                    setStyleName("starrating-inactive-marked");
                } else {
                    setStyleName("starrating-inactive-unmarked");
                }
            }
        }

        /**
         * Initializes the a star object.
         */
        public StarImage() {
            super();
            setElement(DOM.createSpan());
            active = false;
            highlighted = false;
            updateImage();
        }
    }

    // ------------------------------------------------------------------------------

    /**
     * An array with star objects that each represent a star image.
     */
    private StarImage[] stars;

    /**
     * Stores the number of stars that are currently selected.
     */
    private int starsActive = 0;

    /**
     * Stores the position of the star that is currently highlighted by the
     * user.
     */
    private int starsHighlighted = 0;

    /**
     * The label which shows the ratio of selected stars, e.g. (8/10).
     */
    private Label lblRating;

    /**
     * An animated image which is used as a progress indicator.
     */
    private Image imgProgress;

    /**
     * A string which allows a user of this component to store information about
     * the object that is rated by this control. The object type can specifiy an
     * entity or any other user specific string data.
     */
    private String ratedObjectType = null;

    /**
     * A string which allows a user of this component to store information about
     * the object that is rated by this control. The object id can specifiy an
     * ID of a database object or any other user specific integer data.
     */
    private Integer ratedObjectId = null;

    /**
     * Indicates whether a transfer of the currently selected rating to
     * server-side via RPC is currently underway.
     */
    private boolean transferInProgress = false;

    /**
     * The service that is used to retrieve and store rating data.
     */
    private RatingServiceAsync ratingService = null;

    /**
     * Creates a new StarRating-Object and binds it to the specified Object over
     * the supplied RatingService.
     * 
     * @param theNumberOfStars
     *            The number of stars the control will show.
     * @param theRatedObjectId
     *            Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType
     *            Custom string, e.g. an object type.
     * @param theRatingService
     *            The service that is used to retrieve and store rating data.
     * @param submitOnly
     *            Set to true, if the current rating should not be pulled from
     *            the service.
     */
    public StarRating(final int theNumberOfStars, final Integer theRatedObjectId, final String theRatedObjectType,
            final RatingServiceAsync theRatingService, final boolean submitOnly) {
        this(theNumberOfStars, theRatedObjectId, theRatedObjectType);

        AsyncCallback callback = new AsyncCallback() {
            public void onSuccess(final Object result) {
                Integer nr = (Integer) result;
                setRating(nr.intValue());
            }

            public void onFailure(final Throwable caught) {
                // do some UI stuff to show failure
            }
        };

        setRatingService(theRatingService);
        theRatingService.getRating(theRatedObjectId, theRatedObjectType, callback);
    }

    /**
     * Creates a new StarRating-Object and binds it to the specified Object over
     * the supplied RatingService.
     * 
     * @param theNumberOfStars
     *            The number of stars the control will show.
     * @param theRatedObjectId
     *            Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType
     *            Custom string, e.g. an object type.
     * @param theRatingService
     *            The service that is used to retrieve and store rating data.
     */
    public StarRating(final int theNumberOfStars, final Integer theRatedObjectId, final String theRatedObjectType,
            final RatingServiceAsync theRatingService) {
        this(theNumberOfStars, theRatedObjectId, theRatedObjectType, theRatingService, false);
    }

    /**
     * Creates a new StarRating-Object, sets the id and type fields but doesn't
     * bind it to a RatingService.
     * 
     * @param theNumberOfStars
     *            The number of stars the control will show.
     * @param theRatedObjectId
     *            Custom ID that identifies the object the rating belongs to.
     * @param theRatedObjectType
     *            Custom string, e.g. an object type.
     */
    public StarRating(final int theNumberOfStars, final Integer theRatedObjectId, final String theRatedObjectType) {
        super();
        setRatingService(null);
        setRatedObjectId(theRatedObjectId);
        setRatedObjectType(theRatedObjectType);
        initializeComponent();
        initializeDisplay(theNumberOfStars);
    }

    /**
     * Creates a new StarRating-Object without any object ids or service
     * binding.
     * 
     * @param theNumberOfStars
     *            The number of stars the control will show.
     */
    public StarRating(final int theNumberOfStars) {
        this(theNumberOfStars, null, null);
    }

    /**
     * Creates the base DOM element.
     */
    private void initializeComponent() {
        setElement(DOM.createSpan());
        this.setStyleName("starrating");
    }

    /**
     * Creates the Stars, the Label and initializes event handling.
     * 
     * @param theNumberOfStars
     *            The number of stars to display.
     */
    private void initializeDisplay(final int theNumberOfStars) {
        stars = new StarImage[theNumberOfStars];
        for (int i = 0; i < theNumberOfStars; i++) {
            StarImage star = new StarImage();
            stars[i] = star;
            add(star);
        }
        sinkEvents(Event.ONMOUSEOVER | Event.ONMOUSEOUT | Event.ONCLICK);

        lblRating = new Label();
        add(lblRating);
        lblRating.setStyleName("starrating-label");

        imgProgress = new Image("user/starrating/star_indicator.gif");
        imgProgress.setStyleName("starrating-indicator");
        add(imgProgress);

        setTransferInProgress(false);
        updateDisplay();
    }

    /**
     * Convenience method to add child widgets under the base DOM element.
     * 
     * @param widget
     *            The widget to add as a child.
     */
    private void add(final Widget widget) {
        DOM.appendChild(getElement(), widget.getElement());
    }

    /**
     * Determines the number of the star from the DOM element.
     * 
     * @param star
     *            DOM element of a star.
     * @return the number the star, starting with 1
     */
	private int getStarNumber(final Element star) {
        for (int i = 0; i < stars.length; i++) {
            if (DOM.compare(stars[i].getElement(), star)) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Sets the clicked star as active and contacts the RatingService. 
     * @param starNumber The number of the star that was clicked on 
     */
    private void starClicked(final int starNumber) {
        setRating(starNumber);

        if (getRatingService() != null) {
            setTransferInProgress(true);

            AsyncCallback callback = new AsyncCallback() {
                public void onSuccess(final Object result) {
                    setTransferInProgress(false);
                }

                public void onFailure(final Throwable caught) {
                    setTransferInProgress(false);
                }
            };

            getRatingService().setRating(getRatedObjectId(), getRatedObjectType(), new Integer(starNumber), callback);
        }
    }

    /**
     * Displays a hover effect when the mouse hovers over a star.
     * @param starNumber The number of the star that was hovered over 
     */
    private void starHovered(final int starNumber) {
        starsHighlighted = starNumber;
        updateDisplay();
    }

    /**
     * Redisplays all stars based on their current state.
     */
    private void updateDisplay() {
        for (int i = 0; i < stars.length; i++) {
            stars[i].setHighlighted(i < starsHighlighted);
            stars[i].setActive(i < starsActive);
        }
        lblRating.setText("(" + starsActive + "/" + stars.length + ")");
    }

    /**
     * Handles the browser events.
     * @param event a browser event
     * @see com.google.gwt.user.client.ui.Widget#onBrowserEvent(com.google.gwt.user.client.Event)
     */
    public final void onBrowserEvent(final Event event) {
        switch (DOM.eventGetType(event)) {
        case Event.ONMOUSEOVER:
            if (!isTransferInProgress()) {
                starHovered(getStarNumber(DOM.eventGetTarget(event)));
            }
            break;
        case Event.ONMOUSEOUT:
            starHovered(0);
            break;
        case Event.ONCLICK:
            if (!isTransferInProgress()) {
                starClicked(getStarNumber(DOM.eventGetTarget(event)));
            }
            break;
        default:
            super.onBrowserEvent(event);
        }
    }

    /**
     * Returns the current rating as a fraction.
     * @return fraction, a float between 0 and 1
     */
    public final float getFractionRating() {
        return starsActive / stars.length;
    }

    public Integer getRatedObjectId() {
        return ratedObjectId;
    }

    public void setRatedObjectId(Integer ratedObjectId) {
        this.ratedObjectId = ratedObjectId;
    }

    public String getRatedObjectType() {
        return ratedObjectType;
    }

    public void setRatedObjectType(String ratedObjectType) {
        this.ratedObjectType = ratedObjectType;
    }

    public boolean isTransferInProgress() {
        return transferInProgress;
    }

    public void setTransferInProgress(boolean transferInProgress) {
        this.transferInProgress = transferInProgress;
        imgProgress.setVisible(transferInProgress);
    }

    public int getRating() {
        return starsActive;
    }

    public void setRating(int number) {
        this.starsActive = number;
        updateDisplay();
    }

    public RatingServiceAsync getRatingService() {
        return ratingService;
    }

    public void setRatingService(RatingServiceAsync ratingService) {
        this.ratingService = ratingService;
    }
}
