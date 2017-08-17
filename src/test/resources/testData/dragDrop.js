/**
 * 
 */
(function() {

var Component = function( element ) {
  element = document.querySelector( element );
  console.info(element);

  return {
    simulate: simulateFn
  };

  //-------------//

  function simulateFn( elem ) {
    elem = document.querySelector( elem );

    /*Simulating drag start*/
    var type = 'dragstart';
    var event = createEvent( type );
    dispatchEvent( element, type, event );

    /*Simulating drop*/
    type = 'drop';
    var dropEvent = createEvent(type, {});
    dropEvent.dataTransfer = event.dataTransfer;
    dispatchEvent( elem, type, dropEvent );

    /*Simulating drag end*/
    type = 'dragend';
    var dragEndEvent = createEvent(type, {});
    dragEndEvent.dataTransfer = event.dataTransfer;
    dispatchEvent( elem, type, dragEndEvent );
  }

  //------------------//

  function createEvent(type) {

    var event = document.createEvent( 'CustomEvent' );
    event.initCustomEvent( type, true, true, null );

    event.dataTransfer = {
      data: {},
      setData: function(type, val){
        this.data[type] = val;
      },
      getData: function(type){
        return this.data[type];
      }
    };
    return event;
  }

  function dispatchEvent(elem, type, event) {
    if(elem.dispatchEvent) {
      elem.dispatchEvent(event);
    }else if( elem.fireEvent ) {
      elem.fireEvent("on"+type, event);
    }
  }

};

window.Drag = Component;
})();
