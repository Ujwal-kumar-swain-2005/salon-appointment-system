import Banner from './Banner'
import HomeServiceCard from './HomeServiceCard'
import { services } from '../../assets/Data/services'
import SalonList from '../../Salon/SalonList'
const Home = () => {
  return (
    <div className="space-y-20">
      
      <section>
        <Banner />
      </section>

      <section className="px-4 md:px-0">
        <div className="max-w-4xl mx-auto space-y-4">
          
          <h2 className="text-3xl font-semibold text-center">
            Our Services
          </h2>

          <p className="text-center text-gray-600">
            Explore a wide range of beauty and wellness services tailored for you.
          </p>

          <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
            {services.map((item) => (
              <HomeServiceCard
                key={item.id}
                item={item}
              />
            ))}
          </div>

        </div>
      </section>

      <section>
        <h2 className="text-3xl font-semibold text-center mb-6">
          Featured Salons
        </h2>
      </section>
          <section>
                <h2 className="text-3xl font-semibold text-center mb-6">Featured Salons</h2>    
                <SalonList/>
            </section>
    </div>
  )
}

export default Home
