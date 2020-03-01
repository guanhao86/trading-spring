/*function fnFormatDetails ( oTable, nTr )
{
    var aData = oTable.fnGetData( nTr );
    var sOut = '<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">';
    sOut += '<tr><td>Rendering engine:</td><td>'+aData[1]+' '+aData[4]+'</td></tr>';
    sOut += '<tr><td>Link to source:</td><td>Could provide a link here</td></tr>';
    sOut += '<tr><td>Extra info:</td><td>And any further details here (images etc)</td></tr>';
    sOut += '</table>';

    return sOut;
}*/

$(document).ready(function() {
    /*
     * Initialse DataTables, with no sorting on the 'details' column
     */
    var bTable = $('#b_table').dataTable( {
        "aoColumnDefs": [
            { "bSortable": false, "aTargets": [ 0,1,3 ] }
        ],
        bFilter: false,    //去掉搜索框方法三：这种方法可以
        bLengthChange: false,   //去掉每页显示多少条数据方法
        bPaginate: false,
        bLengthChange: false,
        bInfo : false,
        oLanguage: {
            sEmptyTable: "没有数据"
        }
    });

    var sTable = $('#s_table').dataTable( {
        "aoColumnDefs": [
            { "bSortable": false, "aTargets": [ 0,1,3 ] }
        ],
        bFilter: false,    //去掉搜索框方法三：这种方法可以
        bLengthChange: false,   //去掉每页显示多少条数据方法
        bPaginate: false,
        bLengthChange: false,
        bInfo : false,
        oLanguage: {
            sEmptyTable: "没有数据"
        }
    });

    /* Add event listener for opening and closing details
     * Note that the indicator for showing which row is open is not controlled by DataTables,
     * rather it is done here
     */
/*    $(document).on('click','#b_table tbody td img',function () {
        var nTr = $(this).parents('tr')[0];
        if ( bTable.fnIsOpen(nTr) ) {
            bTable.fnClose( nTr );
        } else {
            bTable.fnOpen( nTr, fnFormatDetails(bTable, nTr), 'details' );
        }
    } );

    $(document).on('click','#s_table tbody td img',function () {
        var nTr = $(this).parents('tr')[0];
        if ( sTable.fnIsOpen(nTr) ) {
            sTable.fnClose( nTr );
        } else {
            sTable.fnOpen( nTr, fnFormatDetails(sTable, nTr), 'details' );
        }
    } );*/
} );