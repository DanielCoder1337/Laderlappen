#pragma checksum "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\Pages\Index.razor" "{ff1816ec-aa5e-4d10-87f7-6f4963833460}" "834208e7b1bfe7547759b46c098692525d64e12d"
// <auto-generated/>
#pragma warning disable 1591
namespace WebAppLaderLappen.Pages
{
    #line hidden
    using System;
    using System.Collections.Generic;
    using System.Linq;
    using Microsoft.AspNetCore.Components;
#nullable restore
#line 1 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using System.Net.Http;

#line default
#line hidden
#nullable disable
#nullable restore
#line 2 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using Microsoft.AspNetCore.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 3 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using Microsoft.AspNetCore.Components.Authorization;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using Microsoft.AspNetCore.Components.Forms;

#line default
#line hidden
#nullable disable
#nullable restore
#line 5 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using Microsoft.AspNetCore.Components.Routing;

#line default
#line hidden
#nullable disable
#nullable restore
#line 6 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using Microsoft.AspNetCore.Components.Web;

#line default
#line hidden
#nullable disable
#nullable restore
#line 7 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using Microsoft.JSInterop;

#line default
#line hidden
#nullable disable
#nullable restore
#line 8 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using WebAppLaderLappen;

#line default
#line hidden
#nullable disable
#nullable restore
#line 9 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\_Imports.razor"
using WebAppLaderLappen.Shared;

#line default
#line hidden
#nullable disable
#nullable restore
#line 4 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\Pages\Index.razor"
using WebAppLaderLappen.Data;

#line default
#line hidden
#nullable disable
#nullable restore
#line 5 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\Pages\Index.razor"
using System.Threading;

#line default
#line hidden
#nullable disable
#nullable restore
#line 6 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\Pages\Index.razor"
using System.Threading.Tasks;

#line default
#line hidden
#nullable disable
    [Microsoft.AspNetCore.Components.RouteAttribute("/")]
    public partial class Index : Microsoft.AspNetCore.Components.ComponentBase
    {
        #pragma warning disable 1998
        protected override void BuildRenderTree(Microsoft.AspNetCore.Components.Rendering.RenderTreeBuilder __builder)
        {
            __builder.OpenElement(0, "button");
            __builder.AddAttribute(1, "class", "btn btn-primary");
            __builder.AddAttribute(2, "onclick", Microsoft.AspNetCore.Components.EventCallback.Factory.Create<Microsoft.AspNetCore.Components.Web.MouseEventArgs>(this, 
#nullable restore
#line 8 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\Pages\Index.razor"
                                          GetPos

#line default
#line hidden
#nullable disable
            ));
            __builder.AddContent(3, "Click me");
            __builder.CloseElement();
        }
        #pragma warning restore 1998
#nullable restore
#line 10 "C:\Users\olive\Desktop\webappfirebase\Laderlappen\WebAppLaderLappenApp\WebAppLaderLappen\Pages\Index.razor"
       
    private PositionService positionService;
    private List<Position> positions = new List<Position>();

    protected override async Task OnInitializedAsync()
    {
        positionService = new PositionService();




    }
    protected override async void OnAfterRender(bool firstRender)
    {
        if (firstRender)
        {
            await JS.InvokeAsync<string>("createCanvas");
        }
        else
        {

        }
    }

    private async void GetPos()
    {

        positions = await positionService.GetPositionsAsync();

        for (int i = 0; i < positions.Count; i++)
        {
            await JS.InvokeAsync<string>("addMowerPos", positions[i].x, positions[i].y, positions[i].collision);


        }
        


    }

#line default
#line hidden
#nullable disable
        [global::Microsoft.AspNetCore.Components.InjectAttribute] private IJSRuntime JS { get; set; }
    }
}
#pragma warning restore 1591
