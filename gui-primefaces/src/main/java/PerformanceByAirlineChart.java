import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.chart.PieChartModel;

import com.wwi13amc.twitter_api.business_logic_api.chartObjects.PerformanceByAirline;


@ManagedBean(name="PerformanceByAirlineChart")
@RequestScoped
public class PerformanceByAirlineChart {
	
	public PieChartModel model;

	public PieChartModel getModel() {
		model = PerformanceByAirline.pieChart();
		return model;
	}

	public void setModel(PieChartModel model) {
		this.model = model;
	}
	
	
}
