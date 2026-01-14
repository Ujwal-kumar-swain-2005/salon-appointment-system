import React from "react";
import SalonDetail from "./SalonDetail";
import { Button, Divider } from "@mui/material";
import SalonServiceDetail from "./SalonServiceDetail";
import Review from "../../Customer/Review/Review";
import AddReview from "../../Customer/Review/AddReview";
interface Tab {
  name: string;
}

const tabs: Tab[] = [
  { name: "All Services" },
  { name: "Reviews" },
  { name: "Add Review" },
];

const SalonDetails: React.FC = () => {
  const [activeTab, setActiveTab] = React.useState<string>(tabs[0].name);

  const handleActiveTab = (tabName: string): void => {
    setActiveTab(tabName);
  };

  return (
    <div className="px-5 lg:px-20">
      <SalonDetail />

      <div className="space-y-4">
        <div className="flex gap-2">
          {tabs.map((tab) => (
            <Button
              key={tab.name}
              onClick={() => handleActiveTab(tab.name)}
              variant={activeTab === tab.name ? "contained" : "outlined"}
            >
              {tab.name}
            </Button>
          ))}
        </div>

        <Divider />
      </div>

      <div>
        {activeTab === "All Services" && (
          <SalonServiceDetail />
        )}

        {activeTab === "Reviews" && (
         <div>
            <h2 className='text-2xl font-semibold mb-5'>Reviews</h2>
            <Review />
          </div>
        )}

        {activeTab === "Add Review" && (
          <div>
            <h2 className="text-2xl font-semibold mb-5">Add Review</h2>
            <AddReview />
          </div>
        )}
      </div>
    </div>
  );
};

export default SalonDetails;
